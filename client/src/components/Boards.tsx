import useBoard from "hooks/useBoard";
import BoardItem from "./BoardItem";

export default function Boards() {
  const {
    boardQuery: { isLoading, error, data: boards },
  } = useBoard();

  return (
    <section className="flex flex-col p-6 max-w-5xl mx-auto">
      <h1 className="text-2xl font-bold pb-6 border-b-2 border-brand">
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
