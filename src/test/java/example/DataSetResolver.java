package example;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;


public class DataSetResolver implements ParameterResolver {

    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Parameter parameter = parameterContext.getParameter();
        Class<?> type = parameter.getType();
        if (Stream.class.equals(type)) {
            ParameterizedType parameterizedType = ( ParameterizedType ) parameter.getParameterizedType();
            Type firstGenericArgument = parameterizedType.getActualTypeArguments()[0];
            return DataSet.class.equals(firstGenericArgument);
        }
        return false;
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Executable executable = parameterContext.getDeclaringExecutable();
        RulesFile annotation = executable.getAnnotation(RulesFile.class);
        if (annotation != null) {
            String fileName = annotation.value();
            return DataSet.parseRuleFile(fileName);
        }
        throw new IllegalStateException("Missing @RulesFile annotation on " + executable);
    }

}
