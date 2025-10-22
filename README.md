# Fuyou Calculator

Fuyou Calculator は、給与計算における「扶養・社会保険・住民税」などの“収入の壁”を自動で判定するツールです。  
従業員や家族の年収を入力するだけで、どの扶養区分に該当するかを即座に表示します。  
GUI（グラフィカルユーザーインターフェース）を採用しており、コマンド操作なしで直感的に利用できます。

---

## Overview

| 壁の種類 | 主な内容 |
|-----------|-----------|
| 103万円の壁 | 所得税の扶養控除が外れる |
| 106万円の壁 | 社会保険加入義務が発生（従業員51人以上） |
| 110万円の壁 | 住民税の課税が発生（自治体により異なる） |
| 123万円の壁 | 扶養控除上限（2025年度より引き上げ） |
| 130万円の壁 | 社会保険の扶養から外れる（国保加入が必要） |
| 150万円の壁 | 学生など特例で満額控除を受けられる上限 |

---

## Features

- 年収入力のみで自動判定  
- 各「収入の壁」を可視化（判定結果を色付きで表示）  
- 外部設定ファイル（config）で閾値を管理（ハードコードなし）  
- 設定ファイルの読み込みエラーをハンドリング  
- GUI操作（ボタン・テキスト入力・結果エリア表示）  
- 制度改定（例：2026年10月以降の変更）への柔軟対応  

---

## Project Structure

```
FuyouCalculator/
├── src/
│   ├── Main.java
│   ├── FuyouCalculator.java
│   ├── ConfigLoader.java
│   └── ui/
│       └── FuyouFrame.java
├── config/
│   └── fuyou_config.properties
├── README.md
└── LICENSE
```

---

## Setup

### 1. Clone the repository
```bash
git clone https://github.com/yourname/FuyouCalculator.git
cd FuyouCalculator
```

### 2. Compile the source code
```bash
javac -encoding UTF-8 -d bin src/**/*.java
```

### 3. Run the application
```bash
java -cp bin Main
```

---

## Configuration

収入の壁の閾値は `config/fuyou_config.properties` で設定できます。

```properties
threshold_103 = 1030000
threshold_106 = 1060000
threshold_110 = 1100000
threshold_123 = 1230000
threshold_130 = 1300000
threshold_150 = 1500000
```

設定ファイルが見つからない場合、GUI上にエラーダイアログが表示されます。

---

## Error Handling

- 設定ファイル読み込み失敗時のダイアログ表示  
- 数値以外の入力に対するエラー表示  
- 閾値設定の不備（欠損・重複）検出  
- 異常終了を防ぐ例外処理  

---

## Future Plans

- CSVによる従業員リスト一括判定  
- 判定結果のPDF出力  
- Web版（React / Node.js連携）への拡張  
- 最新税制・社会保険制度の自動更新  

---

## License

```
MIT License
Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
```

---

## Author
Yuri Funato  
Kindai University - Electronic Commerce Laboratory (ECL)  
GitHub: https://github.com/Yu-r-i
