package br.com.hsi.converter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Named
@ApplicationScoped
public class DateTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String stringValue) {

        if (null == stringValue || stringValue.isEmpty()) {
            return null;
        }

        LocalDateTime localDateTime = null;

        try {

            localDateTime = LocalDateTime.parse(
                    stringValue.trim(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").withZone(ZoneId.systemDefault()));

        } catch (DateTimeParseException e) {

            throw new ConverterException("O formato da data e hora deve ser 13/11/2015 12:00:00.");
        }

        return localDateTime;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object localDateTimeValue) {

        if (null == localDateTimeValue) {

            return "";
        }

        return ((LocalDateTime) localDateTimeValue)
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").withZone(ZoneId.systemDefault()));
    }
}
