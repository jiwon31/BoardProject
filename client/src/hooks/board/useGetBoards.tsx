import { useQuery } from "@tanstack/react-query";
import BoardApi from "api/board-api";
import useSearch from "hooks/useSearch";
import { GetBoardListResponse } from "types/board";

export default function useGetBoards(boardApi = new BoardApi()) {
  const { searchParams } = useSearch();

  const getBoardList = useQuery<GetBoardListResponse, Error>(["boards"], () =>
    boardApi.getBoardList(searchParams)
  );

  return { getBoardList };
}
