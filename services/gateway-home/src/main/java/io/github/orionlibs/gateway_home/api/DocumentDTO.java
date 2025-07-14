package io.github.orionlibs.gateway_home.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

public record DocumentDTO(@JsonProperty("document_url") String documentURL, String title,
                          String description, @JsonProperty("created_at") LocalDateTime createdAt,
                          @JsonProperty("updated_at") LocalDateTime updatedAt) implements Serializable
{
}
