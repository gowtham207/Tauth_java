import type React from "react";

export type routerConfigType = {
  path: string;
  component: React.FC;
  isPublic: boolean;
};
