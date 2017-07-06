package ar.com.bank.services.enums;

/**
 * Enum to match maven profiles @see pom.xml
 * 
 * @author sergio.castineyras
 */
public enum Environments {
    LOCAL("local"), DEV("dev"), BANK("bank");

    private String name;

    private Environments(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
