
import { useState } from 'react';
import styled from 'styled-components';
import type { Token } from '.';
import { parsorApi } from './api/parsorApi';
import SqlInput from './component/SqlInput';
import SqlOutput from './component/SqlOutput';


const Container = styled.div`
  max-width: 800px;
  margin: 0 auto;
  padding: 40px 20px;
`;

const Title = styled.h1`
  text-align: center;
  color: #333;
  margin-bottom: 40px;
`;

function App() {
  const [tokens, setTokens] = useState<Token[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  // 인풋에서 사용되는 함수. 백엔드에 api날림
  const handleParse = async (sql: string) => {
    setIsLoading(true);
    try {
      const result = await parsorApi(sql);
      setTokens(result);
    } catch (error) {
      console.error('Parsing failed:', error);
      alert('서버와 통신 중 오류가 발생했습니다. 백엔드가 켜져 있는지 확인해주세요.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Container>
      <Title>(주)메인라인 SQL Parser 과제</Title>
      
      <SqlInput onParse={handleParse} isLoading={isLoading} />
      
      <hr />
      <SqlOutput tokens={tokens} />
    </Container>
  );
}

export default App;