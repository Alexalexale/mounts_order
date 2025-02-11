package br.com.mounts.infrascruture;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Classe que visa facilitar a manipulação de valores que em essência são UUIDs mas podem estar
 * mascarados em diversos formatos.
 */
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(access = PRIVATE)
public class UUIDFacilitator implements Serializable {

  @Serial private static final long serialVersionUID = 1311384158289176362L;

  private static final Pattern UUID_SEPARATOR_PATTERN = Pattern.compile("-");

  private static final String UUID_FORMATTER_REPLACEMENT = "$1-$2-$3-$4-$5";

  private static final String UUID_FORMATTER_REGEX =
      "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)";

  private static final Pattern UUID_FORMATTER_PATTERN = Pattern.compile(UUID_FORMATTER_REGEX);

  private static final int UNFORMATTED_LENGTH = 32;

  UUID uuid;

  public static UUIDFacilitator valueOf(@NonNull final String string) {
    var s = string;

    s = s.replaceAll(UUID_SEPARATOR_PATTERN.pattern(), EMPTY);

    if (s.length() == UNFORMATTED_LENGTH) {
      s = s.replaceFirst(UUID_FORMATTER_PATTERN.pattern(), UUID_FORMATTER_REPLACEMENT);
    }
    try {
      return new UUIDFacilitator(UUID.fromString(s));

    } catch (RuntimeException e) {
      throw new IllegalArgumentException("Malformed code " + string, e);
    }
  }

  public static UUIDFacilitator valueOf(@NonNull final UUID uuid) {
    return new UUIDFacilitator(uuid);
  }

  public static UUIDFacilitator random() {
    return new UUIDFacilitator(UUID.randomUUID());
  }

  public UUID toUUID() {
    return uuid;
  }

  @Override
  public String toString() {
    return uuid.toString();
  }

  public String toUnformattedString() {
    return uuid.toString().toUpperCase().replaceAll(UUID_SEPARATOR_PATTERN.pattern(), EMPTY);
  }
}
