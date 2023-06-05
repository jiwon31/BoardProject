import useLike from "hooks/board/useLike";
import { HiOutlineHeart, HiHeart } from "react-icons/hi";

type LikeButtonProps = {
  boardId: number;
  likeCount: number;
};

export default function LikeButton({ boardId, likeCount }: LikeButtonProps) {
  const { addLikeToBoard, cancelLikeFromBoard } = useLike(boardId);

  const handleAddLike = () => addLikeToBoard.mutate(boardId);
  const handleCancelLike = () => cancelLikeFromBoard.mutate(boardId);

  return (
    <div className="flex items-center gap-x-1 px-2 py-1 border border-gray-300">
      <button onClick={handleAddLike}>
        <HiOutlineHeart className="text-lg" />
      </button>
      <span>{likeCount}</span>
    </div>
  );
}
