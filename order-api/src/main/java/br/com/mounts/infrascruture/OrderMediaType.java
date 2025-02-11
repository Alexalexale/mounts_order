package br.com.mounts.infrascruture;

import java.nio.charset.Charset;
import java.util.Map;
import org.springframework.http.MediaType;

public class OrderMediaType extends MediaType {

  private static final long serialVersionUID = -1258997666065223928L;

  /** Tipo de conteúdo que especifica a versão 1 de uma API. */
  public static final OrderMediaType APPLICATION_V1_PLUS_JSON;

  /** Valor textual do tipo de conteúdo que especifica a versão 1 de uma API. */
  public static final String APPLICATION_V1_PLUS_JSON_UTF8_VALUE =
      "application/mounts.order.api.v1+json;charset=UTF-8";

  static {
    final MediaType v1 = valueOf(APPLICATION_V1_PLUS_JSON_UTF8_VALUE);
    APPLICATION_V1_PLUS_JSON = new OrderMediaType(v1.getType(), v1.getSubtype());
  }

  public OrderMediaType(String type) {
    super(type);
  }

  public OrderMediaType(String type, String subtype) {
    super(type, subtype);
  }

  public OrderMediaType(String type, String subtype, Charset charset) {
    super(type, subtype, charset);
  }

  public OrderMediaType(String type, String subtype, double qualityValue) {
    super(type, subtype, qualityValue);
  }

  public OrderMediaType(MediaType other, Charset charset) {
    super(other, charset);
  }

  public OrderMediaType(MediaType other, Map<String, String> parameters) {
    super(other, parameters);
  }

  public OrderMediaType(String type, String subtype, Map<String, String> parameters) {
    super(type, subtype, parameters);
  }
}
