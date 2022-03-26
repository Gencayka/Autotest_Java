package ru.Chayka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;
import ru.Chayka.journal.JournalEntryType;
import ru.Chayka.journal.JournalFieldType;
import ru.Chayka.exceptions.NoSuchJournalFieldTypeInfoException;
import ru.Chayka.journal.AbstractJsonJournal;
import ru.Chayka.journal.ResponseKey;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Класс предназначен для проведения тестов по Журналированию
 * <br>Абстрактный класс, для каждого сервиса используется отдельный класс-наследник
 * @param <JJ> Класс для хранения списка записей Журнала, наследуется от AbstractJsonJournal
 * @param <RK> Класс для хранения отдельных записей Журнала, наследуется от ResponseKey
 * @param <JET> Enum для хранения данных о типах записей Журнала, наследуется от JournalEntryType
 */
public abstract class JournalValidator
        <JJ extends AbstractJsonJournal<RK>,
                RK extends ResponseKey,
                JET extends JournalEntryType> {
    protected final Logger logger = LoggerFactory.getLogger(JournalValidator.class.getName());

    protected final SoftAssert softAssert;
    protected final ObjectMapper mapper;

    protected final String param13;
    protected final String param14;
    protected final String param10;

    public JournalValidator(String param13, String param14, String param10) {
        softAssert = new SoftAssert();
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        this.param13 = param13;
        this.param14 = param14;
        this.param10 = param10;
    }

    protected abstract void setEntryTypes(JJ journal);

    /**
     * Метод проверяет, что полученный список записей Журнала совпадает с ожидаемым.
     * Если не найдена запись требуемого типа, в SoftAssert записывается соответствующая ошибка 
     * Если найдена запись не требуемого типа, в SoftAssert записывается соответствующая ошибка, 
     * а данная запись удаляется из полученного списка записей и не участвует в дальнейшем тестировании
     * @param journal реальный список записей Журнала
     * @param expectedEntriesTypes ожидаемый список типов записей Журнала
     */
    protected final void checkIfHasAllEntries(JJ journal, List<JET> expectedEntriesTypes) {
        List<RK> realEntries = new ArrayList<>(journal.getResponse());

        ListIterator<JET> expectedEntriesTypesIterator = expectedEntriesTypes.listIterator();
        ListIterator<RK> realEntriesIterator = realEntries.listIterator();

        while (expectedEntriesTypesIterator.hasNext()) {
            JET expectedEntryType = expectedEntriesTypesIterator.next();
            while (realEntriesIterator.hasNext()) {
                if (realEntriesIterator.next().getEntryType() == expectedEntryType) {
                    expectedEntriesTypesIterator.remove();
                    realEntriesIterator.remove();
                    break;
                }
            }
            realEntriesIterator = realEntries.listIterator();
        }

        if (!expectedEntriesTypes.isEmpty()) {
            for (JET expectedEntry : expectedEntriesTypes) {
                softAssert.fail(String.format("\"%s\" (%s) entry not found",
                        expectedEntry.getFieldValueByFieldType(JournalFieldType.PARAM15),
                        expectedEntry.getFieldValueByFieldType(JournalFieldType.PARAM1)));
            }
        }

        if (!realEntries.isEmpty()) {
            for (RK realEntry : realEntries) {
                softAssert.fail(String.format("Unexpected entry found: \"%s\" (%s)",
                        realEntry.getEntryType().getFieldValueByFieldType(JournalFieldType.PARAM15),
                        realEntry.getEntryType().getFieldValueByFieldType(JournalFieldType.PARAM1)));
                journal.getResponse().remove(realEntry);
            }
        }
    }

    /**
     * Метод проверяет следующие параметры записи Журнала:
     * <br>param13 -    обязательный, одинаковый, значение берется из параметров валидатора
     * <br>param14 -    обязательный, одинаковый, значение берется из параметров валидатора
     * <br>param1 -     обязательный, значение берется из JournalEntryType
     * <br>param2 -     обязательный, значение берется из JournalEntryType
     * @param journal журнал
     */
    protected void checkBasicFields(JJ journal){
        checkEqualStringField(JournalFieldType.PARAM13, journal, param13, true);
        checkEqualStringField(JournalFieldType.PARAM14, journal, param14, true);
        checkStringFieldByEntryType(JournalFieldType.PARAM1, journal);
        checkStringFieldByEntryType(JournalFieldType.PARAM2, journal);
    }

    /**
     * Метод проверяет следующие параметры записи Журнала:
     * <br>param4   - обязательный, одинаковый, любое значение
     * <br>param5   - обязательный, одинаковый, любое значение
     * <br>param6   - обязательный, одинаковый, любое значение
     * <br>param7   - обязательный, одинаковый, любое значение
     * <br>param8   - обязательный, одинаковый, любое значение
     * <br>param9   - обязательный, одинаковый, любое значение
     * <br>param10  - обязательный, одинаковый, значение берется из параметров валидатора
     * <br>param11  - обязательный, одинаковый, любое значение
     * <br>param31  - обязательный, одинаковый, любое значение
     * <br>param28  - обязательный, одинаковый, любое значение
     * <br>param32  - обязательный, одинаковый, любое значение
     * <br>param33  - обязательный, одинаковый, любое значение
     * @param journal журнал
     */
    protected void checkTechFields(JJ journal){
        checkEqualStringField(JournalFieldType.PARAM4, journal, true);
        checkEqualStringField(JournalFieldType.PARAM5, journal, true);
        checkEqualStringField(JournalFieldType.PARAM6, journal, true);
        checkEqualStringField(JournalFieldType.PARAM7, journal, true);
        checkEqualStringField(JournalFieldType.PARAM8, journal, true);
        checkEqualStringField(JournalFieldType.PARAM9, journal, true);
        checkEqualStringField(JournalFieldType.PARAM10, journal, param10, true);
        checkEqualStringField(JournalFieldType.PARAM11, journal, true);
        checkEqualStringField(JournalFieldType.PARAM31, journal, true);
        checkEqualStringField(JournalFieldType.PARAM28, journal, true);
        checkIfHasStringField(JournalFieldType.PARAM32, journal, true);
        checkIfHasStringField(JournalFieldType.PARAM33, journal, true);
    }

    /**
     * Метод проверяет, что у всех записей журнала параметр заполнен (значение может быть любое, кроме null)
     * <br>Если параметр не требуется, то наоборот проверяется его отсутствие
     * <br>Если наличие параметр указано в JournalEntryType, то проверяется наличие лишь у тех записей, 
     * у которых он должен быть, isNeeded в таком случае игнорируется
     * @param fieldType тип параметра журнала
     * @param journal журнал
     * @param isNeeded требуемость параметра
     */
    protected void checkIfHasStringField(JournalFieldType fieldType, JJ journal, boolean isNeeded) {
        try {
            for (RK entry : journal.getResponse()) {
                if(entry.getEntryType().getFieldBoolInfoByFieldType(fieldType)){
                    softAssert.assertNotNull(entry.getStringField(fieldType),
                            formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
                } else {
                    softAssert.assertNull(entry.getStringField(fieldType),
                            formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
                }
            }
        } catch (NoSuchJournalFieldTypeInfoException exception){
            if (isNeeded){
                for (RK entry : journal.getResponse()) {
                    softAssert.assertNotNull(entry.getStringField(fieldType),
                            formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
                }
            } else {
                for (RK entry : journal.getResponse()) {
                    softAssert.assertNull(entry.getStringField(fieldType),
                            formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
                }
            }
        }
    }

    /**
     * Метод проверяет, что у всех записей журнала параметр заполнен (значение может быть любое, кроме null).
     * Если наличие параметра указано в JournalEntryType, то проверяется наличие лишь у тех записей, у которых он должен быть
     * @param fieldType тип параметра журнала
     * @param journal журнал
     */
    protected void checkIfHasStringField(JournalFieldType fieldType, JJ journal){
        checkIfHasStringField(fieldType, journal, true);
    }

    /**
     * Метод проверяет, что у всех записей журнала параметр заполнен (значение может быть любое, кроме null).
     * Если наличие параметра указано в JournalEntryType, то проверяется наличие лишь у тех записей, у которых он должен быть
     * @param fieldType тип параметра журнала
     * @param journal журнал
     */
    protected void checkIfHasIntField(JournalFieldType fieldType, JJ journal) {
        try {
            for (RK entry : journal.getResponse()) {
                if(entry.getEntryType().getFieldBoolInfoByFieldType(fieldType)){
                    softAssert.assertNotNull(entry.getIntField(fieldType),
                            formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
                } else {
                    softAssert.assertNull(entry.getIntField(fieldType),
                            formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
                }
            }
        } catch (NoSuchJournalFieldTypeInfoException exception){
            for (RK entry : journal.getResponse()) {
                softAssert.assertNotNull(entry.getIntField(fieldType),
                        formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
            }
        }
    }

    /**
     * Метод проверяет значение параметра записи журнала по ее типу. Значение параметра должно равняться
     * соответствующему значению типа в JournalEntryType
     * @param fieldType тип параметра журнала
     * @param journal журнал
     */
    protected void checkStringFieldByEntryType(JournalFieldType fieldType, JJ journal) {
        for (RK entry : journal.getResponse()) {
            String realValue = entry.getStringField(fieldType);
            String expectedValue = entry.getEntryType().getFieldValueByFieldType(fieldType);
            softAssert.assertEquals(realValue, expectedValue,
                    formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
        }
    }

    /**
     * Метод проверяет значение параметра записи журнала по ее типу. Если в JournalEntryType у данного параметра указано true,
     * то поверяется его равенство ожидаемому результату в аргументе. В противном случае проверяется,
     * что значение параметра равно null
     * @param fieldType тип параметра журнала
     * @param journal журнал
     * @param expectedValue ожидаемое значение
     */
    protected void checkStringFieldByEntryType(JournalFieldType fieldType, JJ journal, String expectedValue) {
        for (RK entry : journal.getResponse()) {
            String realValue = entry.getStringField(fieldType);
            if(entry.getEntryType().getFieldBoolInfoByFieldType(fieldType)){
                softAssert.assertEquals(realValue, expectedValue,
                        formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
            } else {
                softAssert.assertNull(realValue,
                        formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
            }
        }
    }

    /**
     * Метод проверяет, что значение параметра во всех записях журнала одинаковое и равно ожидаемому результату.
     * Если параметр необязательный, то проверяются значения параметра только у тех записей, где он заполнен
     * @param fieldType тип параметра журнала
     * @param journal журнал
     * @param expectedValue ожидаемое значение
     * @param isRequired обязательность параметра
     */
    protected void checkEqualStringField(JournalFieldType fieldType, JJ journal, String expectedValue, boolean isRequired){
        if(isRequired){
            for (RK entry : journal.getResponse()) {
                softAssert.assertEquals(entry.getStringField(fieldType), expectedValue,
                        formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
            }
        } else {
            for (RK entry : journal.getResponse()) {
                if (entry.getStringField(fieldType) != null){
                    softAssert.assertEquals(entry.getStringField(fieldType), expectedValue,
                            formFieldAssertionFailMessage(fieldType.getFieldName(), entry.getParam15(), entry.getParam1()));
                }
            }
        }
    }

    /**
     * Метод проверяет, что значение параметра во всех записях журнала одинаковое. Если поле
     * необязательное, то проверяются значения параметра только у тех записей, где он заполнен
     * @param fieldType тип параметра журнала
     * @param journal журнал
     * @param isRequired обязательность параметра
     */
    protected void checkEqualStringField(JournalFieldType fieldType, JJ journal, boolean isRequired) {
        String expectedValue = null;
        for (RK entry : journal.getResponse()) {
            if(entry.getStringField(fieldType) != null){
                expectedValue = entry.getStringField(fieldType);
                break;
            }
        }
        if(isRequired && expectedValue == null){
            checkIfHasStringField(fieldType, journal);
        } else {
            checkEqualStringField(fieldType, journal, expectedValue, isRequired);
        }
    }

    private String formFieldAssertionFailMessage(String fieldName, String entryMessage, String journalType){
        return String.format("%s check in \"%s\" (%s) entry failed:", fieldName, entryMessage, journalType);
    }
}
