アプリ概要
じゃんけんを CPU と対戦できる Android アプリです。グー・チョキ・パーの
いずれかを選び、先に 3 勝した方を勝者とします。各ラウンドには 5 秒の
制限時間があり、時間内に手を選ばなかった場合はグーとして判定します。

1. 追加した指定機能
・3 先取勝負
	3 勝先取でゲームを終了し、勝敗をダイアログで表示します。ゲーム終了後は
	もう一けんの手のランダム決定、勝敗判定、得点管理、3 勝先取のゲーム終了
	判定を実装しました。
・app/src/main/java/com/example/janken/activity/PlayActivity.java
	5 秒のカウントダウン、手の選択、CPU との対戦、得点表示、ゲーム終了処理を
	実装しました。各ラウンドの結果はデータベースへ保存します。
・app/src/main/java/com/example/janken/database/GameDatabaseHelper.java
	SQLiteDatabase に対戦記録を登録し、最新 50 件を取得する処理を追加しました。
	51 件以上になった場合は古い記録を削除します(ゲーム数ではなくじゃんけん数で数える)。
    

・app/src/main/java/com/example/janken/activity/RecordActivity.java
	RecyclerView を使って過去の対戦記録を一覧表示し、対戦結果と手の統計も表示
	するようにしました。
・app/src/main/java/com/example/janken/model/GameStatistics.java
	記録を集計し、グー・チョキ・パーの出現割合と、それぞれの勝率を計算します。
    tvHandRates と tvWinRates に集計結果を表示する処理を追加しました。

・app/src/main/java/com/example/janken/activity/StatisticsActivity.java
	各手の出現率・勝率と、勝ち・負け・引き分けの回数を表示します。

3. 動作確認の結果
	じゃんけんの手を選択すると、CPU の手と結果が表示され、勝ちまたは負けの
	得点が増加しました。3 勝に到達するとゲーム終了ダイアログが表示され、
	再戦または終了を選択できました。各ラウンドの記録は履歴画面に保存され、
	記録に基づく出現割合・勝率も統計画面に表示されました。
	また、5 秒以内に手を選択しなかった場合も、グーとして正常にラウンドが
	判定されました。

4. その他の追加機能
・各ラウンドの制限時間を 5 秒に設定
・グー・チョキ・パーそれぞれの出現割合を表示
・グー・チョキ・パーそれぞれの勝率を表示
・勝ち、負け、引き分けの回数を表示
・対戦時のカウントダウン、勝敗、引き分けの効果音を再生
・設定画面で音量を変更

5. 実装過程
	まず JankenGame に手の種類、勝敗判定、得点、3 勝先取のルールを実装し、
	PlayActivity から利用できるようにしました。次に CountDownTimer を使って
	5 秒の制限時間を追加し、ラウンド終了時に GameRecord を生成して
	GameDatabaseHelper へ保存する処理を実装しました。
	その後、RecordActivity と RecordAdapter で保存済みの記録を一覧表示し、
	GameStatistics で手の出現割合と手ごとの勝率を集計しました。最後に
	StatisticsActivity へ集計結果を表示し、実際に対戦、時間切れ、3 勝到達、
	履歴表示、統計表示を確認しました。度対戦するか、対戦を終了するかを選択できます。
・過去の対戦記録の閲覧
	各ラウンドの自分の手、CPU の手、結果、日時を保存し、記録画面で新しい
	順に確認できます。保存する記録は最新 20 件です。

2. 改良したファイルと内容
・app/src/main/java/com/example/janken/model/JankenGame.java
	じゃん
