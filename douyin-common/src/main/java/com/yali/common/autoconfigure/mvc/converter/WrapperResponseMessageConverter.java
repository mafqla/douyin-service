package com.yali.common.autoconfigure.mvc.converter;

import com.yali.common.utils.WebUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

/**
 * 这个类是一个自定义的HttpMessageConverter，它包装了一个MappingJackson2HttpMessageConverter。
 * 它用于转换HTTP请求和响应。
 */
public class WrapperResponseMessageConverter implements HttpMessageConverter<Object> {

    // 这个类包装的MappingJackson2HttpMessageConverter。
    private final MappingJackson2HttpMessageConverter delegate;

    /**
     * WrapperResponseMessageConverter的构造函数。
     *
     * @param mappingJackson2HttpMessageConverter 被包装的MappingJackson2HttpMessageConverter。
     */
    public WrapperResponseMessageConverter(
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        this.delegate = mappingJackson2HttpMessageConverter;
    }

    /**
     * 这个方法没有实现，总是返回false。
     */
    @Override
    public boolean canRead(@NonNull Class<?> clazz, MediaType mediaType) {
        return false;
    }

    /**
     * 这个方法检查请求是否是网关请求，以及委托是否可以写入给定的类和媒体类型。
     */
    @Override
    public boolean canWrite(@NonNull Class<?> clazz, MediaType mediaType) {
        return WebUtils.isGatewayRequest() && delegate.canWrite(clazz, mediaType);
    }

    /**
     * 这个方法返回委托支持的媒体类型。
     */
    @Override
    @NonNull
    public List<MediaType> getSupportedMediaTypes() {
        return delegate.getSupportedMediaTypes();
    }

    /**
     * 这个方法读取输入消息并将其转换为给定类的对象。
     */
    @Override
    @NonNull
    public Object read(@NonNull Class<?> clazz, @NonNull HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return delegate.read(clazz, inputMessage);
    }

    /**
     * 这个方法将给定的对象写入输出消息。
     */
    @Override
    public void write(@NonNull Object o, MediaType contentType, @NonNull HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        delegate.write(o, contentType, outputMessage);
    }
}