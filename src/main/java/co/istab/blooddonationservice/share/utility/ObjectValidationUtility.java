package co.istab.blooddonationservice.share.utility;

import java.util.Objects;
import java.util.Optional;

public class ObjectValidationUtility {

  public static <T> T updateValue(T t, T o) {
    if (Objects.isNull(t)) return o;

    return Optional.of(t).orElse(o);
  }
}
