import { useMutation } from "@tanstack/react-query";
import AuthApi from "api/auth-api";
import { SignUpRequest } from "types/auth";

export default function useAuth(authApi = new AuthApi()) {
  const signUp = useMutation((data: SignUpRequest) => authApi.signUp(data));

  return { signUp };
}
