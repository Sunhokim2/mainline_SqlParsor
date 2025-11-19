export type TokenType = 
  | 'KEYWORD'
  | 'FUNCTION'
  | 'STRING'
  | '주석'
  | 'BINDING'
  | 'ETC';

export interface Token {
  value: string;
  type: TokenType;
}


export interface SqlRequest {
  sql: string;
}