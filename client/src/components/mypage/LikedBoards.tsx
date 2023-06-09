import BoardItem from "components/board/BoardItem";
import useLikedBoard from "hooks/mypage/useLikedBoard";
import { useState, useEffect } from "react";
import { BsChevronDown } from "react-icons/bs";
import { Board } from "types/board";

export default function MyBoards() {
  const [likedBoards, setMyBoards] = useState<Board[]>([]);
  const [isLastPage, setIsLastPage] = useState<boolean>(true);
  const [page, setPage] = useState<number>(1);
  const {
    likedBoardsQuery: { isLoading, error, data },
    getLikedBoardList,
  } = useLikedBoard();

  const handleMoreBtnClick = () => {
    getLikedBoardList.mutate(page, {
      onSuccess: (data) => {
        setMyBoards((prev) => [...prev, ...data.boards]);
        setIsLastPage(data.isLast);
        setPage((prev) => prev + 1);
      },
    });
  };

  useEffect(() => {
    if (data) {
      setMyBoards([...data.boards]);
      setIsLastPage(data.isLast);
    }
  }, [data]);

  return (
    <section className="ml-1 flex-1">
      <h1 className="text-2xl font-bold pb-5 border-b-2 border-gray-200">
        좋아요한 글
      </h1>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      <ul className="flex flex-col gap-y-2">
        {likedBoards.length > 0 &&
          likedBoards.map((board) => (
            <BoardItem key={board.id} board={board} />
          ))}
      </ul>
      {!isLastPage && (
        <div className="flex justify-center py-4">
          <button
            className="flex items-center px-10"
            onClick={handleMoreBtnClick}
          >
            <span>더보기</span>
            <BsChevronDown />
          </button>
        </div>
      )}
    </section>
  );
}
