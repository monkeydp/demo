package com.monkeydp.demo.protobuf.server;

import cn.bintools.daios.common.util.main.StringUtil;
import com.google.protobuf.MessageLite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.monkeydp.demo.protobuf.server.MediaType.APPLICATION_PROTOBUF_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * @author iPotato
 * @date 2019/7/14
 */
@AutoConfigureMockMvc
public abstract class BaseControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions mvcGet(String url) throws Exception {
        MockHttpServletRequestBuilder builder = get(fullUrl(url));
        return mockMvc.perform(builder);
    }

    protected ResultActions mvcPut(String url, MessageLite data) throws Exception {
        byte[] dataBytes = this.convert2byteArray(data);
        MockHttpServletRequestBuilder builder = put(fullUrl(url)).contentType(APPLICATION_PROTOBUF_VALUE)
                                                                 .content(dataBytes);
        return mockMvc.perform(builder);
    }

    private byte[] convert2byteArray(MessageLite data) throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();) {
            data.writeTo(stream);
            return stream.toByteArray();
        }
    }

    protected byte[] mvcGetAndReturnByteArray(String url) throws Exception {
        ResultActions resultActions = this.mvcGet(url);
        return resultActions.andReturn()
                            .getResponse()
                            .getContentAsByteArray();
    }

    protected byte[] mvcPutAndReturnByteArray(String url, MessageLite data) throws Exception {
        ResultActions resultActions = this.mvcPut(url, data);
        return resultActions.andReturn()
                            .getResponse()
                            .getContentAsByteArray();
    }

    private String fullUrl(String url) {
        String urlPrefix = this.getUrlPrefix();
        StringBuilder builder = new StringBuilder();
        builder.append("/")
               .append(StringUtil.isEmpty(urlPrefix) ? "" : urlPrefix + "/")
               .append(url);
        return builder.toString();
    }

    /**
     * URL 前缀
     *
     * @return
     */
    protected String getUrlPrefix() {

        final String suffix = "ControllerTest";
        Class childClass = this.getClass();
        String simpleName = childClass.getSimpleName();
        String specificName = StringUtil.removeSuffix(simpleName, suffix);

        if (specificName.equals(simpleName)) {
            throw new RuntimeException(String.format("测试控制器后缀名必须为「%s」", suffix));
        }

        String urlPrefix = StringUtil.toLowerUnderscore(specificName);

        if (StringUtil.isEmpty(urlPrefix)) {
            throw new RuntimeException("测试控制器的 URL 前缀不能为空");
        }

        return urlPrefix;
    }
}
