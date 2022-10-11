package com.TsuberaKristian.database;

import java.util.ArrayList;
import java.util.List;

public class Calculated {

    public double calculation(String expressionText){
        List<Symbol> symbols = symbolAnalyze(expressionText);
        SymbolBuff symbolBuff = new SymbolBuff(symbols);
        return expr(symbolBuff);

    }

    private enum symbolsType {
        LEFT_BKT, RIGHT_BKT, OP_PLUS, OP_MINUS,
        OP_MUL, OP_DIV,
        NUMBER,
        EOF
    }

    private static class Symbol {
        symbolsType type;
        String value;

        public Symbol(symbolsType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Symbol(symbolsType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Symbol{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    private static class SymbolBuff {
        private int pos;
        public List<Symbol> symbols;

        public SymbolBuff(List<Symbol> symbols) {
            this.symbols = symbols;
        }

        public Symbol next() {
            return symbols.get(pos++);
        }

        public int getPos() {
            return pos;
        }

        public void back() {
            pos--;
        }

    }

    private static List<Symbol> symbolAnalyze(String expText) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int pos = 0;
        int dot_count = 0;
        while (pos < expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    symbols.add(new Symbol(symbolsType.LEFT_BKT, c));
                    pos++;
                    continue;

                case ')':
                    symbols.add(new Symbol(symbolsType.RIGHT_BKT, c));
                    pos++;
                    continue;
                case '+':
                    symbols.add(new Symbol(symbolsType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    symbols.add(new Symbol(symbolsType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    symbols.add(new Symbol(symbolsType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    symbols.add(new Symbol(symbolsType.OP_DIV, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;

                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                            if (c=='.'&& dot_count<1){
                                sb.append(c);
                                dot_count++;
                                pos++;
                            }
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);

                        } while (c <= '9' && c >= '0' || c == '.');
                        symbols.add(new Symbol(symbolsType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new ArithmeticException("Unexpected character: " + c);
                        }
                        pos++;
                    }
            }

        }
        symbols.add(new Symbol(symbolsType.EOF, ""));
        return symbols;
    }

    private static double expr(SymbolBuff symbolsBuff) {
        Symbol symbol = symbolsBuff.next();
        if (symbol.type == symbolsType.EOF) {
            return 0;
        } else {
            symbolsBuff.back();
            return plus_minus(symbolsBuff);
        }

    }

    private static double plus_minus(SymbolBuff symbolsBuff) {
        double value = mult_div(symbolsBuff);
        while (true) {
            Symbol symbol = symbolsBuff.next();
            switch (symbol.type) {
                case OP_PLUS:
                    value += mult_div(symbolsBuff);
                    break;
                case OP_MINUS:
                    value -= mult_div(symbolsBuff);
                    break;
                case EOF:
                case RIGHT_BKT:
                    symbolsBuff.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + symbol.value
                            + " at position: " + symbolsBuff.getPos());
            }

        }
    }

    private static double mult_div(SymbolBuff symbolsBuff) {
        double value = factor(symbolsBuff);
        while (true) {
            Symbol symbol = symbolsBuff.next();
            switch (symbol.type) {
                case OP_MUL:
                    value *= factor(symbolsBuff);
                    break;
                case OP_DIV:
                    value /= factor(symbolsBuff);
                    break;
                case EOF:
                case RIGHT_BKT:
                case OP_PLUS:
                case OP_MINUS:
                    symbolsBuff.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + symbol.value
                            + " at position: " + symbolsBuff.getPos());
            }
        }
    }

    private static double factor(SymbolBuff symbolsBuff) {
        Symbol symbol = symbolsBuff.next();
        switch (symbol.type) {
            case OP_MINUS:
                double value_min = factor(symbolsBuff);
                return -value_min;
            case NUMBER:
                return Double.parseDouble(symbol.value);
            case LEFT_BKT:
                double value = expr(symbolsBuff);
                symbol = symbolsBuff.next();
                if (symbol.type != symbolsType.RIGHT_BKT) {
                    throw new ArithmeticException("Unexpected token " + symbol.value + " at position " + symbolsBuff.getPos());
                }
                return value;
            default:
                throw new ArithmeticException("Unexpected token " + symbol.value + " at position " + symbolsBuff.getPos());
        }
    }
}
