package co.istad.dara.pipeline_service.stream;


public class DataXmlDeserializer extends XmlStringDeserializer<Data>{
    public DataXmlDeserializer() {
        super(Data.class);
    }
}

