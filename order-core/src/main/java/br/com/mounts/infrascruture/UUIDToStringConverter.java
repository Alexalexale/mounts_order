package br.com.mounts.infrascruture;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class UUIDToStringConverter implements AttributeConverter<UUID, String> {

  @Override
  public String convertToDatabaseColumn(UUID uuid) {
    return (uuid != null) ? uuid.toString().replace("-", "").toUpperCase() : null;
  }

  @Override
  public UUID convertToEntityAttribute(String dbData) {
    final var format = "$1-$2-$3-$4-$5";
    final var regexUUID =
        "([a-fA-F0-9]{8})([a-fA-F0-9]{4})([a-fA-F0-9]{4})([a-fA-F0-9]{4})([a-fA-F0-9]{12})";
    return (dbData != null) ? UUID.fromString(dbData.replaceAll(regexUUID, format)) : null;
  }
}
