import { useQuery } from "@tanstack/react-query";
import UserApi from "api/user-api";
import { Board } from "types/board";
import useRecoilUser from "../useRecoilUser";

export default function useMyBoard(userApi = new UserApi()) {
  const { user } = useRecoilUser();

  const myBoardsQuery = useQuery<Board[], Error>(
    ["boards", { userId: user?.id }],
    userApi.getMyBoardList,
    { staleTime: 1000 * 60 * 5, enabled: !!user }
  );

  return { myBoardsQuery };
}
