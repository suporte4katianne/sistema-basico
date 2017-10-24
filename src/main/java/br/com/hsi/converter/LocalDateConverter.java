package br.com.hsi.converter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Named
@ApplicationScoped
public class LocalDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String stringValue) {

        if (null == stringValue || stringValue.isEmpty()) {
            return null;
        }

        LocalDate localDate = null;

        try {

            localDate = LocalDate.parse(
                    stringValue.trim(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault()));

        } catch (DateTimeParseException e) {
            e.printStackTrace();

        }

        return localDate;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object localDateValue) {

        if (null == localDateValue) {

            return "";
        }

        return ((LocalDate) localDateValue)
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault()));
    }
}
