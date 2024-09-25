package org.cuspid.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cuspid.util.Strings;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class MustacheString {

    @Getter(value = PRIVATE)
    private String self;

    // transformed properties
    private String uppercase;
    private String lowercase;
    private String firstLowercase;

    public static MustacheString of(String self) {
        MustacheString mustacheString = new MustacheString();
        mustacheString.self = self;
        mustacheString.config();
        return mustacheString;
    }

    private void config() {
        this.uppercase = self.toUpperCase();
        this.lowercase = self.toLowerCase();
        this.firstLowercase = Strings.firstLowerCase(self);
    }

    @Override
    public String toString() {
        return self;
    }
}
