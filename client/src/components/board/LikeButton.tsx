import useLike from "hooks/board/useLike";
import { HiOutlineHeart, HiHeart } from "react-icons/hi";

type LikeButtonProps = {
  boardId: number;
  likeCount: number;
  isLiked: boolean;
};

export default function LikeButton({
  boardId,
  likeCount,
  isLiked,
}: LikeButtonProps) {
  const { addLikeToBoard, cancelLikeFromBoard } = useLike(boardId);

  const handleAddLike = () => addLikeToBoard.mutate(boardId);
  const handleCancelLike = () => cancelLikeFromBoard.mutate(boardId);

  return (
    <div className="flex items-center gap-x-1 px-2 py-1 border border-gray-300">
      {!isLiked ? (
        <button onClick={handleAddLike}>
          <HiOutlineHeart className="text-xl text-red-500" />
        </button>
      ) : (
        <button onClick={handleCancelLike}>
          <HiHeart className="text-xl text-red-500" />
        </button>
      )}
      <span>{likeCount}</span>
    </div>
  );
}
