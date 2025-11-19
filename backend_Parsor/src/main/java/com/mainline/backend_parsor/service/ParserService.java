package com.mainline.backend_parsor.service;

import com.mainline.backend_parsor.dto.SqlResponse;
import com.mainline.backend_parsor.dto.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ParserService {

    // 유일한 것이니까 List가아닌 Set을 사용합니다.
    private static final Set<String> KEYWORDS = new HashSet<>();
    private static final Set<String> FUNCTIONS = new HashSet<>();

    static {
        Set<String> kws = Set.of("SELECT", "FROM", "WHERE", "AND", "LIKE", "OR", "ORDER", "BY", "GROUP", "INSERT", "UPDATE", "DELETE");
        KEYWORDS.addAll(kws);

        Set<String> fns = Set.of("MAX", "SUM", "MIN", "AVG", "COUNT");
        FUNCTIONS.addAll(fns);
    }

    // sql문자열을 토큰리스트로 반환하는 함수
    public List<SqlResponse> parse(String sql) {
        List<SqlResponse> tokens = new ArrayList<>();

        if (sql == null || sql.isEmpty()) return tokens;

        // 그냥 스트링 안쓰는 이유 : 문자열 하나씩 읽어서 합치는 방식이라 그냥 String사용하면 메모리 낭비가 너무 심해진다. 자바에서 String은 불변성이라 포인터를 바꾸면서 변환하는 성질때문이다.
        StringBuilder buffer = new StringBuilder();
        int length = sql.length();

        // 문자열 하나씩 읽기
        for (int i = 0; i < length; ++i) {
            char c = sql.charAt(i);
            char nextC = (i + 1 < length) ? sql.charAt(i + 1) : '\0';

            // 주석 처리
            // * 한 줄 주석
            if (c == '-' && nextC == '-') {
                flushBuffer(tokens, buffer); // 이전에 쌓인 단어 처리
                StringBuilder comment = new StringBuilder();
                while (i < length) {
                    char current = sql.charAt(i);
                log.info("현재 내용 : "+current);
                    if (current == '\n') break;
                    comment.append(current);
                    i++;
                }
                tokens.add(new SqlResponse(comment.toString(), TokenType.주석));
                log.info("i값 : "+i);
                log.info("내용 : "+comment);
                i--; // 루프 인덱스 보정
                continue;
            }

            // 멀티 라인 주석
            if (c == '/' && nextC == '*') {
                flushBuffer(tokens, buffer);
                StringBuilder comment = new StringBuilder();
                comment.append(c).append(nextC);
                i += 2; // /* 처리함

                while (i < length) {
                    char current = sql.charAt(i);
                    char next = (i + 1 < length) ? sql.charAt(i + 1) : '\0';
                    comment.append(current);
                    if (current == '*' && next == '/') {
                        comment.append(next);
                        i++;
                        break;
                    }
                    i++;
                }
                tokens.add(new SqlResponse(comment.toString(), TokenType.주석));
                continue;
            }

            // 문자열 처리 (따옴표 확인)
            if (c == '\'') {
                flushBuffer(tokens, buffer);
                StringBuilder stringToken = new StringBuilder();
                stringToken.append(c);
                i++;

                while (i < length) {
                    char current = sql.charAt(i);
                    stringToken.append(current);
                    if (current == '\'') break;
                    i++;
                }
                tokens.add(new SqlResponse(stringToken.toString(), TokenType.STRING));
                continue;
            }

            // 바인딩 변수 ':' 이표시 있는것
            if (c == ':') {
                flushBuffer(tokens, buffer);
                StringBuilder bindToken = new StringBuilder();
                bindToken.append(c);
                i++;

                while (i < length) {
                    char current = sql.charAt(i);
                    // 변수명은 영문, 숫자, _ 만 가능하다고 가정
                    if (!Character.isLetterOrDigit(current) && current != '_') {
                        i--;
                        break;
                    }
                    bindToken.append(current);
                    i++;
                }
                tokens.add(new SqlResponse(bindToken.toString(), TokenType.BINDING));
                continue;
            }

            // 그 외 처리 (ETC, 키워드 등)
            if (Character.isWhitespace(c) || isSymbol(c)) {
                flushBuffer(tokens, buffer); // 지금까지 쌓인 단어 처리

                tokens.add(new SqlResponse(String.valueOf(c), TokenType.ETC));
            } else {
                // 단어의 일부면 버퍼에 쌓음
                buffer.append(c);
            }
        }

        // 남은 버퍼 처리
        flushBuffer(tokens, buffer);

        return tokens;
    }

    // 특수 기호인지 확인하는 함수
    private boolean isSymbol(char c) {
        // c가 있는지 확인. 있는 인덱스 값인데 없으면 -1
        return "(),=;*".indexOf(c) >= 0;
    }

    // 버퍼에 쌓인 단어를 분석해서 리스트에 추가하는 함수, 키워드와 함수, ETC를 여기서 처리한다.
    private void flushBuffer(List<SqlResponse> tokens, StringBuilder buffer) {
        log.info("플러시버퍼 목록 : "+buffer);
        if (buffer.isEmpty()) return;

        String word = buffer.toString();
        String upperWord = word.toUpperCase();

        if (KEYWORDS.contains(upperWord)) {
            tokens.add(new SqlResponse(word, TokenType.KEYWORD));
        } else if (FUNCTIONS.contains(upperWord)) {
            tokens.add(new SqlResponse(word, TokenType.FUNCTION));
        } else {
            tokens.add(new SqlResponse(word, TokenType.ETC));
        }

        buffer.setLength(0); // 버퍼 비우기
    }
}