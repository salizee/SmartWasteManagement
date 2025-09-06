SmartWaste (Room + RecyclerView + Edit/Delete) - Android Studio project
-----------------------------------------------------------------------
Features:
- Splash screen (fade-in)
- Hardcoded login (admin / 1234)
- Register (stores simple users in SharedPreferences for demo)
- Dashboard with RecyclerView listing pickup requests (Room persists requests)
- Add/Edit/Delete requests (Room)
- Dark theme, material look

How to import:
1. Download and unzip SmartWaste_room.zip to D:\SmartWaste
2. Open Android Studio -> File -> Open -> select the extracted folder (containing settings.gradle)
3. When prompted choose the bundled JDK and let Gradle sync
4. Build -> Run on an emulator or device
5. Login with admin / 1234, then add requests and test edit/delete

Notes:
- For demo simplicity .allowMainThreadQueries() is used in Room. For production move DB ops off the main thread.
- If Gradle asks to update components, accept default suggestions.
