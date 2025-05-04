package utils;

import exceptions.NullStringException;
import functions.TypeOfClassMap;

import java.util.Scanner;
import java.util.function.Function;

public class InputFieldBuilder<T> {
    private Scanner sc;
    private Class<T> type;
    private String start;
    private String inputParams;
    private String againMsg;
    private String againMsgParams;
    private String end;



    private InputFieldBuilder(Scanner sc, Class<T> type) {
        this.sc = sc;
        this.type = type;
    }

    public static <T> InputFieldBuilder<T> build(Scanner sc, Class<T> type) {
        return new InputFieldBuilder<T>(sc, type);
    }

    public InputFieldBuilder<T> start(String start) {
        this.start = start;
        return this;
    }

    public InputFieldBuilder<T> inputParams(String inputParams) {
        this.inputParams = inputParams;
        return this;
    }

    public InputFieldBuilder<T> againMsg(String againMsg) {
        this.againMsg = againMsg;
        return this;
    }
    public InputFieldBuilder<T> againMsgParams(String againMsgParams) {
        this.againMsgParams = againMsgParams;
        return this;
    }

    public InputFieldBuilder<T> end(String end) {
        this.end = end;
        return this;
    }

    public T build() {
        while (true) {
            System.out.println(start);
            System.out.println(inputParams);
            String input;

            if (type.equals(String.class)) {
                input = sc.nextLine();
            } else {
                input = sc.next();
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }

            try {

                if (input.isEmpty() || input.trim().isEmpty()) {
                    throw new NullStringException();
                }

                Function<String, ?> parser = TypeOfClassMap.classMap.get(type);
                if (parser == null) {
                    throw new UnsupportedOperationException("Неизвестный тип данных, для исчезновения ошибки поправьте класс InputFieldBuilder" + type);
                }
                T result = type.cast(parser.apply(input));
                System.out.println(end);
                return result;

            } catch (UnsupportedOperationException eee) {
                System.out.println(eee.getMessage());
            } catch (NumberFormatException eee) {
                System.out.println(againMsgParams);
            } catch (NullStringException eee) {
                System.out.println(eee.getMessage());
                System.out.println(againMsg);
            } catch (IllegalArgumentException eee) {
                System.out.println(againMsg);
            }
        }
    }

}
