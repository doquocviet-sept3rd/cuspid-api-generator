package org.cuspid.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cuspid.util.Generators;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class MustacheClass {
    @Getter(value = PRIVATE)
    private Class<?> self;

    // transformed properties
    private MustacheString fullName;
    private MustacheString name;
    // For entity id
    private MustacheClass id;

    public static MustacheClass of(Class<?> self) {
        MustacheClass mustacheString = new MustacheClass();
        mustacheString.self = self;
        mustacheString.config();
        return mustacheString;
    }

    private void config() {
        this.fullName = MustacheString.of(self.getName());
        this.name = MustacheString.of(self.getSimpleName());
        try {
            this.id = MustacheClass.of(Generators.findClazzId(self));
        } catch (Exception ignore) {
            // do nothing
        }
    }

    @Override
    public String toString() {
        throw new IllegalCallerException("MustacheClass self is an object");
    }
}
