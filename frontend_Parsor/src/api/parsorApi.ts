import type { SqlRequest, Token } from "..";
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const parsorApi = async (sql: string): Promise<Token[]> =>{
    const response = await axios.post<Token[]>(`${API_BASE_URL}/parse`, {
    sql
  } as SqlRequest);
  return response.data;
}