package app.data_ingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import app.data_ingestion.dataLayer.models.ValidationRule;
import app.data_ingestion.services.validationAndIngestion.IValidationRulesService;
import app.data_ingestion.services.validationAndIngestion.ValidationRulesService;

@SpringBootTest
@ContextConfiguration
public class StringValidationRulesServiceTest {

    @Test
    void stringValidationNotNullInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("not null");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        String expected = String.format("%s %s", "NAME", rule.getOperator());
        assertEquals(expected, service.validate(rules, "NAME", "", mapColumnToDatatype));
        ;

    }

    @Test
    void stringValidationNotNullValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("not null");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        assertEquals("", service.validate(rules, "NAME", "TestName", mapColumnToDatatype));
        ;

    }

    @Test
    void stringValidationEqualsToValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("EQUALS TO");
        rule.setRhsValue("Testname");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        assertEquals("", service.validate(rules, "NAME", "TestName", mapColumnToDatatype));
        ;

    }

    @Test
    void stringValidationEqualsToInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("EQUALS TO");
        rule.setRhsValue("TestnameInvalid");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        String expected = String.format("%s %s %s", "NAME", rule.getOperator(), rule.getRhsValue());
        assertEquals(expected, service.validate(rules, "NAME", "TestName", mapColumnToDatatype));
        ;

    }

    @Test
    void stringValidationInvalidMaxLength() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("MAX LENGTH");
        rule.setRhsValue("invalidMaxLength");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        assertThrows(NumberFormatException.class,
                () -> service.validate(rules, "NAME", "TestName", mapColumnToDatatype));

    }

    @Test
    void stringValidationMaxLengthValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("MAX LENGTH");
        rule.setRhsValue("5");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        assertEquals("", service.validate(rules, "NAME", "TestN", mapColumnToDatatype));

    }

    @Test
    void stringValidationMaxLengthInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("MAX LENGTH");
        rule.setRhsValue("5");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        String expected = String.format("%s %s %s", "NAME", rule.getOperator(), rule.getRhsValue());
        assertEquals(expected, service.validate(rules, "NAME", "TestName", mapColumnToDatatype));

    }

    @Test
    void stringValidationNotContainsInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("NOT CONTAINS");
        rule.setRhsValue("test");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        String expected = String.format("%s %s %s", "NAME", rule.getOperator(), rule.getRhsValue());
        assertEquals(expected, service.validate(rules, "NAME", "TestName", mapColumnToDatatype));

    }

    @Test
    void stringValidationNotContainsValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("NOT CONTAINS");
        rule.setRhsValue("1234");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        assertEquals("", service.validate(rules, "NAME", "TestName", mapColumnToDatatype));

    }

    @Test
    void stringValidationContainsInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("CONTAINS");
        rule.setRhsValue("1234");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        String expected = String.format("%s %s %s", "NAME", rule.getOperator(), rule.getRhsValue());
        assertEquals(expected, service.validate(rules, "NAME", "TestName", mapColumnToDatatype));

    }

    @Test
    void stringValidationContainsValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("CONTAINS");
        rule.setRhsValue("name");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("NAME", "STRING");

        IValidationRulesService service = new ValidationRulesService();
        assertEquals("", service.validate(rules, "NAME", "TestName", mapColumnToDatatype));

    }
}
