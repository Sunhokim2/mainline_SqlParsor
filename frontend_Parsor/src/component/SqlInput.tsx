import React, { useState } from 'react';
import styled from 'styled-components';

interface SqlInputProps {
  onParse: (sql: string) => void;
  isLoading: boolean;
}

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
  margin-bottom: 20px;
`;

const TextArea = styled.textarea`
  width: 100%;
  height: 150px;
  padding: 15px;
  font-size: 16px;
  border: 2px solid #ddd;
  border-radius: 8px;
  resize: vertical;
  outline: none;

  &:focus {
    border-color: #617185ff;
  }
`;

const ParseButton = styled.button`
  padding: 12px;
  font-size: 16px;
  font-weight: bold;
  color: white;
  background-color: #4a90e2;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
  height: 100px;

  &:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
`;

const SqlInput: React.FC<SqlInputProps> = ({ onParse, isLoading }) => {
  const [sql, setSql] = useState('');

  return (
    <InputContainer>
      <h3>SQL 입력</h3>
      <TextArea
        placeholder="파싱할 SQL문을 입력"
        value={sql}
        onChange={(e) => setSql(e.target.value)}
      />
      <ParseButton onClick={() => onParse(sql)} disabled={isLoading || !sql.trim()}>
        {isLoading ? '로딩 중' : '분석시작'}
      </ParseButton>
    </InputContainer>
  );
};

export default SqlInput;