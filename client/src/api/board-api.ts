import { Board, BoardRequest } from "types/board.d";
import { instance } from "./axios.config";

export default class BoardApi {
  async createBoard(data: BoardRequest) {
    const response = await instance.post<number>("/boards", data);
    return response.data;
  }

  async updateBoard(boardId: number, data: BoardRequest) {
    const response = await instance.put<Board>(`/boards/${boardId}`, data);
    return response.data;
  }

  async deleteBoard(boardId: number) {
    return instance.patch<void>(`/boards/${boardId}`);
  }

  async getSingleBoard(boardId: number) {
    const response = await instance.get<Board>(`/boards/${boardId}`);
    return response.data;
  }

  // TODO: 파라미터 & 페이징
  async getBoardList() {
    const response = await instance.get<Board[]>("/boards");
    return response.data;
  }
}
