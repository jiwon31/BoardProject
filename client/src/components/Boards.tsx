import { useEffect } from "react";
import BoardItem from "./BoardItem";
import useBoard from "hooks/useBoard";
import useSearch from "hooks/useSearch";

export default function Boards() {
  const { searchParams } = useSearch();
  const {
    boardQuery: { isLoading, error, data: boards, refetch },
  } = useBoard();

  useEffect(() => {
    refetch();
  }, [searchParams, refetch]);

  return (
    <section className="flex flex-col py-10 max-w-5xl mx-auto">
      <h1 className="text-2xl font-bold pb-5 border-b-2 border-gray-200">
        게시글 목록
      </h1>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      <ul className="flex flex-col gap-y-2">
        {boards &&
          boards.map((board) => <BoardItem key={board.id} board={board} />)}
      </ul>
    </section>
  );
}
