import useBoard from "hooks/useBoard";
import BoardItem from "./BoardItem";

export default function Boards() {
  const {
    boardQuery: { isLoading, error, data: boards },
  } = useBoard();

  return (
    <>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      <ul>{boards && boards.map((board) => <BoardItem />)}</ul>
    </>
  );
}
