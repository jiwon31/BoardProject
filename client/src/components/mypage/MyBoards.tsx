import BoardItem from "components/board/BoardItem";
import useMyBoard from "hooks/useMyBoard";

export default function MyBoards() {
  const {
    myBoardsQuery: { isLoading, error, data: boards },
  } = useMyBoard();

  return (
    <section className="ml-1 flex-1">
      <h1 className="text-2xl font-bold pb-5 mb-5 border-b-2 border-gray-200">
        내가 작성한 글
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
