package cz.fel.cvut.via.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Record
{
        
    @XmlElement(required = true)
    private String transport;

    @XmlElement(required = true)
    private String finish_station;
    
    @XmlElement(required = false)
    private String timestamp;
    
    
    public Record()
    {
    	
    }

    
    public Record(String str1, String str2, String str3)
    {
        this.transport    = str1;
        this.finish_station   = str2;
        this.timestamp = str3;
    }

}
