import React from 'react';
import styled from 'styled-components';
import type { Token } from '..';

interface SqlOutputProps {
  tokens: Token[];
}


const OutputContainer = styled.div`
  width: 100%;
  padding: 20px;
  background-color: black;
  color: #4cf384b9;
  font-weight: bold;
  border-radius: 8px;
  font-size: 14px;
  line-height: 1.5;
  overflow-y: auto;
`;
const Item = styled.div`
  padding: 4px 0;
  &:last-child {
    border-bottom: none;
  }
`;

// tokens를 백에서 받아서 처리
const SqlOutput: React.FC<SqlOutputProps> = ({ tokens }) => {
  if (tokens.length === 0) return null;
  const etcTokens = tokens.filter(token => token.type === 'ETC' && token.value.trim().length > 0);
  
  return (
    <div>
       <h3>실행 결과</h3>
        <OutputContainer>
            ================= 실행 결과 =================
            {tokens.filter(token=> token.type !== 'ETC')
                .map((token, index) =>{
                return (
                    <Item key={index}>
                        {token.value.trim()} {' '}
                        <span style = {{ color: 'white'}}>
                            &rarr; {' '}
                        </span>
                        <span style={{ color: '#7a93adff', fontWeight: 'normal' }}>
                            {token.type.trim()}
                        </span>
                    </Item>
                );
            })}
            <Item>
                {etcTokens.map(t => t.value.trim()).join(' ')}
                <span style = {{ color: 'white'}}>
                    &rarr; {' '}
                </span> 
                <span style={{ color: '#7a93adff', fontWeight: 'normal' }}>
                    그 밖의 모든 내용은 ETC 로 출력한다
                </span>
            </Item>
        </OutputContainer>
    </div>
  );
};

export default SqlOutput;