package org.example;

public class Checker {
    String[] bannedWords = {"http","https","Http","Https","@","buvingdi","amiga","qotoq","qo'toq","President","Shavkat","Mizrziyoyev","Mizrziyayev","Davlat","Konstitutsiya","MIB", "Ichki ishlar vazirligi"};
    public CheckerResponse messageChecker(String input){
        for (int i = 0; i < bannedWords.length; i++) {
            if (input.contains(bannedWords[i])) return new CheckerResponse(bannedWords[i],false);
        }
        return new CheckerResponse("Success", true);
    }
    public boolean numberChecker(String input){
        if (input.startsWith("+998") && input.length() != 13) return false;
        if (input.startsWith("+48") && input.length() != 12) return false;
        return true;
    }
}
