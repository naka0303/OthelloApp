## オセロゲーム

# コマ配置時パターン
パターン1
  BWB、WBW
パターン2
  BWWB、WBBW
パターン3
  BB、WW
パターン4
  BW-、WB-
パターン5
  BW外、WB外

自コマが見つかるまで探索
  - 「自コマが見つかった」「-が見つかった」「盤外」の場合は探索終了(フラグはfalse)
     - 「自コマが見つかった」場合はリスト保持
     - 「-が見つかった」「盤外」場合はリスト初期化
  - 他コマが見つかった場合は探索継続((フラグはtrue))
