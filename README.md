# 📝 Notes App

A modern, feature-rich Android notes application with a clean Material Design interface, dark mode support, and powerful note management capabilities.

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

## ✨ Features

### Core Functionality
- ✅ **Create, Read, Update, Delete** notes
- 🔍 **Real-time search** - Search through titles and descriptions
- 📱 **Material Design 3** - Modern, clean interface
- 🎨 **Staggered Grid Layout** - Pinterest-style note display
- 💾 **SQLite Database** - Local data persistence

### Advanced Features
- 🌓 **Dark/Light Theme** - Three modes: Light, Dark, and System Default
- 🎯 **Multi-Select Mode** - Long press to select and delete multiple notes
- ⚡ **Background Operations** - Smooth UI with background database operations
- 🔄 **Singleton Pattern** - Optimized database management
- 📊 **Empty State** - Clean empty view when no notes exist

## 📱 Screenshots

> <img src = "https://github.com/Choudhuri312/Notes-App/blob/main/Home_screen.jpg" width = "100">
&emsp;
> <img src = "https://github.com/Choudhuri312/Notes-App/blob/main/Selection_mode_screen.jpg" width = "100">
&emsp;
> <img src = "https://github.com/Choudhuri312/Notes-App/blob/main/view_update_scren.jpg" width = "100">

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 21 or higher
- Java 8 or higher

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/notes-app.git
cd notes-app
```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory
   - Click OK

3. **Build and Run**
   - Connect your Android device or start an emulator
   - Click Run ▶️ or press `Shift + F10`

## 🏗️ Architecture

### Project Structure
```
app/
├── src/main/
│   ├── java/com/example/notes/
│   │   ├── DbClass.java           # SQLite database handler
│   │   ├── MainActivity.java      # Main notes list screen
│   │   ├── InsertNote.java        # Add/Edit note screen
│   │   ├── NotesAdapter.java      # RecyclerView adapter
│   │   ├── ModelClass.java        # Note data model
│   │   └── ThemeManager.java      # Theme switching logic
│   ├── res/
│   │   ├── layout/                # XML layouts
│   │   ├── menu/                  # Menu resources
│   │   ├── drawable/              # Icons and drawables
│   │   ├── values/                # Light theme colors
│   │   └── values-night/          # Dark theme colors
```

### Database Schema
```sql
CREATE TABLE notes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    noteTitle TEXT,
    noteDesc TEXT
);
```

## 🎨 Themes

The app supports three theme modes:

1. **Light Mode** - Clean, bright interface
2. **Dark Mode** - Easy on the eyes for night use
3. **System Default** - Follows device theme settings

Toggle between themes using the moon/sun icon in the toolbar.

## 🛠️ Technical Details

### Key Technologies
- **Language**: Java
- **UI Framework**: Material Design 3
- **Database**: SQLite with custom SQLiteOpenHelper
- **Layout**: StaggeredGridLayoutManager
- **Threading**: ExecutorService for background operations
- **Architecture Pattern**: Singleton for database management

### Design Patterns Used
- ✅ Singleton Pattern (Database)
- ✅ ViewHolder Pattern (RecyclerView)
- ✅ Observer Pattern (LiveData concept via manual updates)

### Performance Optimizations
- Background thread for all database operations
- Proper cursor management and cleanup
- Application context for singleton to prevent memory leaks
- Optimized RecyclerView with `getAdapterPosition()`

## 📋 Features Breakdown

### Search Functionality
- Real-time search as you type
- Searches both title and description
- Case-insensitive matching
- SQL LIKE query with wildcards

### Multi-Select & Delete
- Long press any note to enter selection mode
- Tap notes to toggle selection
- Select All option in toolbar
- Visual feedback with border highlight
- Batch delete multiple notes

### Theme System
- Persistent theme preference using SharedPreferences
- Smooth theme transitions
- Dynamic icon updates (moon ↔ sun)
- Material 3 color system

## 🐛 Known Issues

None currently! 🎉

## 🔮 Future Enhancements

- [ ] Note categories/tags
- [ ] Image attachments
- [ ] Rich text editing
- [ ] Cloud sync
- [ ] Note sharing
- [ ] Reminders/notifications
- [ ] Note pinning
- [ ] Export/import notes
- [ ] Trash/Archive functionality

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Your Name**
- GitHub: [@Choudhuri312](https://github.com/yourusername)
- Email: choudhuriabhi.sd@gmail.com

## 🙏 Acknowledgments

- Material Design 3 by Google
- Android Developer Documentation
- Stack Overflow community

## 📞 Support

If you have any questions or run into issues, please open an issue on GitHub.

---

⭐ **Star this repo** if you found it helpful!
