package ar.com.bank.services.utils;

/**
 * Component to read properties depending of language
 */
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BuilderApiMessageUtil {

    @Autowired
    MessageSource messageSource;

    private Locale locale = new Locale.Builder().setLanguage("es").setRegion("AR").build();

    /**
     * Constructor by default.
     */
    public BuilderApiMessageUtil() {
        super();
    }

    /**
     * @param locale
     */
    public BuilderApiMessageUtil(Locale locale) {
        super();
        this.locale = locale;
    }

    public ApiMessage buildMessage(HttpStatus status, String keyCodeMessage, String keyMessage,
            boolean addMessageError) {

        String message = messageSource.getMessage(keyMessage, null, locale);
        String code = null;
        if (StringUtils.isNotBlank(keyCodeMessage)) {
            code = messageSource.getMessage(keyCodeMessage, null, locale);
        }
        ApiMessage apiMessage = new ApiMessage(status, code, message);
        if (addMessageError) {
            apiMessage.addError(message);
        }
        return apiMessage;
    }

    public String getMessage(String keyMessage) {
        return messageSource.getMessage(keyMessage, null, locale);
    }
}
