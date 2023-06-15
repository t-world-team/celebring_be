package com.tworld.celebring.celeb.model;

public enum CelebSearch {

    KR1("ㄱ", "가", "나"),
    KR2("ㄴ", "나", "다"),
    KR3("ㄷ", "다", "라"),
    KR4("ㄹ", "라", "마"),
    KR5("ㅁ", "마", "바"),
    KR6("ㅂ", "바", "사"),
    KR7("ㅅ", "사", "아"),
    KR8("ㅇ", "아", "자"),
    KR9("ㅈ", "자", "차"),
    KR10("ㅊ", "차", "카"),
    KR11("ㅋ", "카", "타"),
    KR12("ㅌ", "타", "파"),
    KR13("ㅍ", "파", "하"),
    KR14("ㅎ", "하", "힣"),

    ENG1("A", "a", "b"),
    ENG2("B", "b", "c"),
    ENG3("C", "c", "d"),
    ENG4("D", "d", "e"),
    ENG5("E", "e", "f"),
    ENG6("F", "f", "g"),
    ENG7("G", "g", "h"),
    ENG8("H", "h", "i"),
    ENG9("I", "i", "j"),
    ENG10("J", "j", "k"),
    ENG11("K", "k", "l"),
    ENG12("L", "l", "m"),
    ENG13("M", "m", "n"),
    ENG14("N", "n", "o"),
    ENG15("O", "o", "p"),
    ENG16("P", "p", "q"),
    ENG17("Q", "q", "r"),
    ENG18("R", "r", "s"),
    ENG19("S", "s", "t"),
    ENG20("T", "t", "u"),
    ENG21("U", "u", "v"),
    ENG22("V",  "v", "w"),
    ENG23("W",  "w", "x"),
    ENG24("X",  "x", "y"),
    ENG25("Y",  "y", "z"),
    ENG26("Z", "z", "가"),

    ETC("기타", " ", "a");

    private String consonant;
    private String startConsonant;
    private String endConsonant;

    CelebSearch(String consonant, String startConsonant, String endConsonant) {
        this.consonant = consonant;
        this.startConsonant = startConsonant;
        this.endConsonant = endConsonant;
    }

    public String getConsonant() {
        return consonant;
    }

    public String getStartConsonant() {
        return startConsonant;
    }

    public String getEndConsonant() {
        return endConsonant;
    }
}
