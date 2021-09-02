lexer grammar Comments;

BLOCK_COMMENT:  '/*' .*? '*/'  -> channel(HIDDEN);
INLINE_COMMENT: (('-- ' | '#') ~[\r\n]* ('\r'? '\n' | EOF) | '--' ('\r'? '\n' | EOF)) -> channel(HIDDEN);
