import { Board, GetBoardListResponse, UpdateBoardRequest } from "types/board.d";
import { instance } from "./axios.config";

export default class BoardApi {
  async createBoard(data: FormData) {
    const response = await instance.post<{id: number}>("/boards", data, { headers: {
      "Content-Type": "multipart/form-data" }});
    return response.data;
  }

  async updateBoard(data: UpdateBoardRequest) {
    const { id, info } = data;
    const response = await instance.put<Board>(`/boards/${id}`, info);
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

  async getBoardList(searchParams?: URLSearchParams) {
    const response = await instance.get<GetBoardListResponse>("/boards", {
      params: searchParams
    });
    return response.data;
  }
}
