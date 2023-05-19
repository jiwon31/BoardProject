import { Board, BoardContent, UpdateBoardRequest } from "types/board.d";
import { instance } from "./axios.config";

export default class BoardApi {
  async createBoard(data: BoardContent) {
    const response = await instance.post<{id: number}>("/boards", data);
    return response.data;
  }

  async updateBoard(data: UpdateBoardRequest) {
    const response = await instance.put<Board>(`/boards/${data.id}`, data.info);
    return response.data;
  }

  async deleteBoard(boardId: number) {
    const response = await instance.patch<void>(`/boards/${boardId}`);
    return response.data;
  }

  async getSingleBoard(boardId: number) {
    const response = await instance.get<Board>(`/boards/${boardId}`);
    return response.data;
  }

  // TODO: 파라미터 & 페이징
  async getBoardList(searchParams?: URLSearchParams) {
    const response = await instance.get<Board[]>("/boards", {
      params: searchParams
    });
    return response.data;
  }
}
