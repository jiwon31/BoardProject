import { useRecoilState } from "recoil";
import { userState } from "store/user-state";

export default function useRecoilUser() {
  const [user, setUser] = useRecoilState(userState);

  return { user, setUser };
}
