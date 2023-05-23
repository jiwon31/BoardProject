import { useEffect } from "react";
import BoardItem from "./BoardItem";
import useBoard from "hooks/useBoard";
import useSearch from "hooks/useSearch";
import Pagination from "./Pagination";

export default function Boards() {
  const {
    boardQuery: { isLoading, error, data, refetch },
  } = useBoard();
  const boards = data?.boards;
  const { searchParams } = useSearch();

  useEffect(() => {
    refetch();
  }, [searchParams, refetch]);

  /** 검색했을 때 삭제된 게시글은 보여주지 않는다. */
  const boardList =
    searchParams.toString() && boards
      ? boards.filter((board) => !board.isDeleted)
      : boards;

  return (
    <section className="flex flex-col py-10 max-w-5xl mx-auto">
      <h1 className="text-2xl font-bold pb-5 border-b-2 border-gray-200">
        게시글 목록
      </h1>
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
