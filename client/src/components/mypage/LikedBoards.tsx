import BoardItem from "components/board/BoardItem";
import useLikedBoard from "hooks/mypage/useLikedBoard";

export default function LikedBoards() {
  const {
    likedBoardsQuery: { isLoading, error, data: boards },
  } = useLikedBoard();

  return (
    <section className="ml-1 flex-1">
      <h1 className="text-2xl font-bold pb-5 border-b-2 border-gray-200">
        좋아요한 글
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
