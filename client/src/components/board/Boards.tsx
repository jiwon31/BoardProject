import { useEffect } from "react";
import BoardItem from "./BoardItem";
import useSearch from "hooks/useSearch";
import Pagination from "../Pagination";
import useGetBoards from "hooks/board/useGetBoards";
import BoardSort from "./BoardSort";

export default function Boards() {
  const {
    getBoardList: { isLoading, error, data, refetch },
  } = useGetBoards();
  const boards = data?.boards;
  const { searchParams } = useSearch();

  useEffect(() => {
    refetch();
  }, [searchParams, refetch]);

  /** 검색했을 때 삭제된 게시글은 보여주지 않는다. */
  const regExp = /(?:title)|(?:content)/;
  const boardList =
    boards && regExp.test(searchParams.toString())
      ? boards.filter((board) => !board.isDeleted)
      : boards;

  return (
    <section className="flex flex-col py-10 max-w-5xl mx-auto">
      <div className="flex justify-between items-center pb-5 border-b-2 border-gray-200">
        <h1 className="text-2xl font-bold ">게시글 목록</h1>
        <BoardSort />
      </div>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      <div className="flex flex-col gap-y-7">
        <ul className="flex flex-col gap-y-2">
          {boardList &&
            boardList.map((board) => (
              <BoardItem key={board.id} board={board} />
            ))}
        </ul>
        {boardList && boardList.length > 0 && (
          <Pagination totalPages={data?.totalPages} />
        )}
      </div>
    </section>
  );
}
