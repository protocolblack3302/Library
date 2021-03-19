package com.coderme.Library.Services;

import com.coderme.Library.Converters.UrlToBookConverter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import java.io.File;


@Data
@Slf4j
@EnableIntegration
@Service
public class BookScannerService {
    private final GenericSelector<File> genericSelector;
    private final UrlToBookConverter transformer;
    private final FileReadingMessageSource fileReadingMessageSource;
    private final MessageHandler messageHandler;


    @Bean
    public IntegrationFlow getIntegrationFlow() {
        return IntegrationFlows.from(fileReadingMessageSource, sourcePollingChannelAdapterSpec ->
                sourcePollingChannelAdapterSpec
                        .poller(Pollers.fixedDelay(500)))
                .filter(genericSelector)
                .transform(transformer)
                .handle(getMessageHandler())
                .get();

    }


}
