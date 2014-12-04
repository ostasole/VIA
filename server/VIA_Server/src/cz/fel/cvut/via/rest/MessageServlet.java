package cz.fel.cvut.via.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 * Record resource.
 */
@Path("records")
public class MessageServlet {
	
    private static DataSource dataSource;
    private static int version;

    public MessageServlet() {
    	version = 1;
        try {
            dataSource = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/via_db");
        } catch (Exception e) {
            System.err.println("Cannot connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void increaseVersion() {
    	version++;
    }

    @GET
    @Path("station/{station}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Record> getMessagesByAuthor(@PathParam("station") String station) throws SQLException {
        
    	Connection connection = null;
        PreparedStatement statement = null;
        
        Time tm = null;
        

        try {
        	
            connection = dataSource.getConnection();
            
            statement = connection.prepareStatement(	"SELECT records.transport, records.finish_station, (records.time - now()::time) " +
        												"FROM records " +
        												"WHERE 	   	(start_station = ? ) "
        														+ "AND (records.time - now()::time)<'01:00:00' "
        														+ "AND (records.time - now()::time)>'00:00:00' " +
        												"ORDER BY (records.time - now()::time) ASC; ");
            
            statement.setString(1, station);
            
            //statement = connection.prepareStatement("SELECT * FROM records;");

            ResultSet results = statement.executeQuery();
            
            

            if (!results.isBeforeFirst())
            {
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            List<Record> records = new ArrayList<Record>();

            while (results.next())
            {
            	tm = results.getTime(3);
                records.add(new Record(results.getString(1), results.getString(2), tm.toString()));
            }

            return records;
        }
        finally
        {
            if (statement != null)
            {
                try
                {
                    statement.close();
                }
                catch (SQLException e)
                {
                    /* At least should be logged. */
                }
            }

            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    /* At least should be logged. */
                }
            }
        }
    }
    
    @GET
    @Path("version")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces(MediaType.TEXT_PLAIN)
    public String getVersion() {
		return Integer.toString(version);    	
    }
    
    
}
