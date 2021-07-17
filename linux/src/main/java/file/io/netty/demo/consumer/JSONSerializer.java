package file.io.netty.demo.consumer;


import com.google.gson.Gson;

public class JSONSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        
        return new Gson().toJson(object).getBytes();
        
    }
    
    
    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        
        return new Gson().fromJson(new String(bytes), clazz);
        
    }
}
