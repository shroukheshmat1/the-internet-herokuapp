# 🚀 GitHub Setup Guide

This guide will help you publish your Selenium automation project to GitHub!

## Step 1: Create a GitHub Account (if you don't have one)

1. Go to [GitHub.com](https://github.com)
2. Click "Sign up" and create your account
3. Verify your email address

## Step 2: Install Git (if not already installed)

### Windows:
1. Download Git from: https://git-scm.com/download/win
2. Run the installer (use default settings)
3. Open Git Bash or Command Prompt to verify:
   ```bash
   git --version
   ```

### Mac:
```bash
# Install via Homebrew
brew install git

# Or download from: https://git-scm.com/download/mac
```

### Linux:
```bash
sudo apt-get install git
```

## Step 3: Configure Git (First Time Only)

Open Git Bash or Terminal and run:

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

## Step 4: Initialize Git in Your Project

1. Open Git Bash or Terminal
2. Navigate to your project folder:
   ```bash
   cd H:/shrouk/Testing/TheInternetTask
   ```

3. Initialize Git repository:
   ```bash
   git init
   ```

## Step 5: Create a New Repository on GitHub

1. Go to [GitHub.com](https://github.com) and sign in
2. Click the **"+"** icon in the top right corner
3. Select **"New repository"**
4. Fill in the details:
   - **Repository name**: `TheInternetTask` (or any name you like)
   - **Description**: "Selenium WebDriver Automation Testing - Beginner Friendly Project"
   - **Visibility**: Choose Public (so others can learn from it) or Private
   - **DO NOT** check "Initialize with README" (we already have one)
5. Click **"Create repository"**

## Step 6: Connect Your Local Project to GitHub

After creating the repository, GitHub will show you commands. Use these:

```bash
# Add all files to Git
git add .

# Create your first commit
git commit -m "Initial commit: Selenium automation tests with educational comments"

# Add the remote repository (replace YOUR_USERNAME with your GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/TheInternetTask.git

# Rename the default branch to main (if needed)
git branch -M main

# Push your code to GitHub
git push -u origin main
```

**Note**: You'll be asked for your GitHub username and password (or Personal Access Token).

## Step 7: Using Personal Access Token (Recommended)

GitHub no longer accepts passwords. You need a Personal Access Token:

1. Go to GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Give it a name (e.g., "My Selenium Project")
4. Select scopes: Check **"repo"** (this gives full control of private repositories)
5. Click "Generate token"
6. **COPY THE TOKEN** (you won't see it again!)
7. When Git asks for password, paste the token instead

## Step 8: Verify Your Upload

1. Go to your GitHub repository page
2. You should see all your files including:
   - README.md
   - pom.xml
   - src/ folder with all your test files
   - .gitignore

## Step 9: Future Updates

Whenever you make changes to your code:

```bash
# Check what files changed
git status

# Add changed files
git add .

# Commit with a message
git commit -m "Description of what you changed"

# Push to GitHub
git push
```

## Common Commands Reference

```bash
# Check status
git status

# See what changed
git diff

# Add specific file
git add filename.java

# Commit changes
git commit -m "Your commit message"

# Push to GitHub
git push

# Pull latest changes (if working on multiple computers)
git pull

# See commit history
git log
```

## Troubleshooting

### Issue: "fatal: not a git repository"
**Solution**: Make sure you're in the project folder and run `git init`

### Issue: "Permission denied"
**Solution**: Check your GitHub username and use Personal Access Token instead of password

### Issue: "Updates were rejected"
**Solution**: Someone else pushed changes. Run `git pull` first, then `git push`

### Issue: Files not showing on GitHub
**Solution**: Make sure you ran `git add .` and `git commit` before `git push`

## 🎉 Congratulations!

Your project is now on GitHub! Share the repository link with others so they can learn from your code!

---

**Need Help?** Check out [GitHub Docs](https://docs.github.com/) or [Git Documentation](https://git-scm.com/doc)

